package org.xyz.chatsvc.client.model;

public record TxnReqPayload(
        String partnerReferenceNo,
        PaymentInfo paymentInfo,
        String remarks
) {
}
