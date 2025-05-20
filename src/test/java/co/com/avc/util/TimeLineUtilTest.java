package co.com.avc.util;

import co.com.avc.mapper.IndexTimeLineMapper;
import co.com.avc.models.dynamo.AcctInfoDto;
import co.com.avc.models.dynamo.DynamoSpiDto;
import co.com.ath.opensearch.logs.constants.ActionConstants;
import co.com.ath.opensearch.logs.constants.TimeStampEnum;
import co.com.ath.opensearch.logs.constants.TypeServiceConstants;
import co.com.ath.opensearch.logs.entity.index_timeline.OSIndexTimeline;
import co.com.ath.opensearch.logs.service.OpensearchLogService;
import co.com.avc.cornerconn.models.Account;
import co.com.avc.cornerconn.models.Customer;
import co.com.avc.cornerconn.models.HeadersRq;
import co.com.avc.cornerconn.models.Product;
import co.com.avc.cornerconn.models.enrollment.EnrollmentRq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TimeLineUtilTest {

    @Mock
    private OpensearchLogService opensearchLogService;

    @Mock
    private IndexTimeLineMapper indexTimeLineMapper;

    @Mock
    private SnsSelectorUtil snsSelectorUtil;

    private TimeLineUtil timeLineUtil;
    private EnrollmentRq enrollmentRq;
    private HeadersRq headersRq;
    private DynamoSpiDto dynamoSpiDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        timeLineUtil = new TimeLineUtil(opensearchLogService, indexTimeLineMapper, snsSelectorUtil);

        // Configurar enrollmentRq con estructura completa para evitar NullPointerException
        enrollmentRq = new EnrollmentRq();
        enrollmentRq.setRequestDateTime("2024-09-20T10:15:30Z");
        enrollmentRq.setTermsAndConditions(true);

        Customer customer = new Customer();
        enrollmentRq.setCustomer(customer);

        Product product = new Product();
        Account account = new Account();
        account.setBankId("testBankId");
        product.setAccount(account);
        enrollmentRq.setProduct(product);

        // Configurar headersRq
        headersRq = new HeadersRq();

        // Configurar dynamoSpiDto
        dynamoSpiDto = new DynamoSpiDto();
        AcctInfoDto acctInfoDto = new AcctInfoDto();
        acctInfoDto.setBankId("testBankId");
        dynamoSpiDto.setAcctInfo(acctInfoDto);

        // Configurar comportamiento de snsSelectorUtil para evitar NullPointerException
        when(snsSelectorUtil.selectSNS(anyString())).thenReturn("testSNS");
    }

    @Test
    void testSendLogRq() {
        // Arrange
        OSIndexTimeline osIndexTimeline = new OSIndexTimeline();
        when(indexTimeLineMapper.mapEnrollmentToTimeLine(
                eq(enrollmentRq),
                eq(headersRq),
                eq(TypeServiceConstants.REQUEST))).thenReturn(osIndexTimeline);

        // Act
        timeLineUtil.sendLogRq(enrollmentRq, headersRq);

        // Assert
        verify(indexTimeLineMapper).mapEnrollmentToTimeLine(
                enrollmentRq, headersRq, TypeServiceConstants.REQUEST);
        verify(snsSelectorUtil).selectSNS("testBankId");
        verify(opensearchLogService).sendSNSOpenSearchLogs(
                eq(osIndexTimeline),
                eq("testSNS"),
                eq(ActionConstants.ONLINE_ENROLL),
                eq(TimeStampEnum.FED_ENROLLMENT_REQUEST));
    }

    @Test
    void testSendLogRsWithEnrollmentRq() {
        // Arrange
        OSIndexTimeline osIndexTimeline = new OSIndexTimeline();
        when(indexTimeLineMapper.mapEnrollmentToTimeLine(
                eq(enrollmentRq),
                eq(headersRq),
                eq(TypeServiceConstants.RESPONSE))).thenReturn(osIndexTimeline);

        // Act
        timeLineUtil.sendLogRs(enrollmentRq, headersRq);

        // Assert
        verify(indexTimeLineMapper).mapEnrollmentToTimeLine(
                enrollmentRq, headersRq, TypeServiceConstants.RESPONSE);
        verify(snsSelectorUtil).selectSNS("testBankId");
        verify(opensearchLogService).sendSNSOpenSearchLogs(
                eq(osIndexTimeline),
                eq("testSNS"),
                eq(ActionConstants.ONLINE_ENROLL),
                eq(TimeStampEnum.FED_ENROLLMENT_RESPONSE));
    }

    @Test
    void testSendLogRsWithDynamoSpiDto() {
        // Arrange
        OSIndexTimeline osIndexTimeline = new OSIndexTimeline();
        String rqUUID = "testUUID";
        when(indexTimeLineMapper.mapDynamoToTimeLine(eq(dynamoSpiDto), eq(rqUUID))).thenReturn(osIndexTimeline);

        // Act
        timeLineUtil.sendLogRs(dynamoSpiDto, rqUUID);

        // Assert
        verify(indexTimeLineMapper).mapDynamoToTimeLine(dynamoSpiDto, rqUUID);
        verify(snsSelectorUtil).selectSNS("testBankId");
        verify(opensearchLogService).sendSNSOpenSearchLogs(
                eq(osIndexTimeline),
                eq("testSNS"),
                eq(ActionConstants.ONLINE_ENROLL),
                eq(TimeStampEnum.CUST_ENROLLMENT_RESPONSE));
    }
}