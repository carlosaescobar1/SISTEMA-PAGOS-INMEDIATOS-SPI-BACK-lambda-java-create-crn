package co.com.avc.mapper;

import co.com.avc.cornerconn.models.request.CornersHeadersRq;

import java.util.List;

public class HeadersMapper {

    /**
    private final CornersHeadersRq headersRq = new CornersHeadersRq();

    private final ParamBankUUID paramBankUUID;
    private final IpSelectorUtil ipSelectorUtil;
    private final List<OriginDto> listOriginDto;

    public CornersHeadersRq headersMapper(String bankId, String rqId, String UUID, String dateOperation) {

        BankUUIDSelectorUItil bankUUIDSelectorUItil = new BankUUIDSelectorUItil(paramBankUUID);
        OriginSelectorUtil originSelectorUtil = new OriginSelectorUtil(listOriginDto);

        headersRq.setDate(dateOperation);
        headersRq.setForwardedFor(ipSelectorUtil.getIp(bankId));
        headersRq.setContentType(DefaultValueEnum.APPLICATION_JSON.getValue());
        headersRq.setRequestId(UUID);
        headersRq.setRbmFrom(bankUUIDSelectorUItil.getBankUUID(bankId));
        headersRq.setOrigin(originSelectorUtil.originSelector(bankId));
        headersRq.setRqUID(rqId);
        headersRq.setChannel(ConstantsEnum.HEADER_CHANNEL.getValue());
        headersRq.setAccept(DefaultValueEnum.APPLICATION_JSON.getValue());

        return headersRq;

    }
        */
}
