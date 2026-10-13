package org.xyz.chatsvc.client.model;
import java.util.List;

public record PaymentInfo(
        String statementDescriptor,
        String currency,
        List<Link> links,
        String name,
        String email,
        String mobile,
        Double amount
) {
}

record Link(
        String cancelRedirectUrl,
        String redirectUrl,
        String callbackUrl
){}
