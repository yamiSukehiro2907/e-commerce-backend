package com.e_commerce.backend_api.webhook;

import com.e_commerce.backend_api.dtos.WebHookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentMockService {

    public WebHookResponse updatePaymentStatus() {
        return new WebHookResponse(new UUID(12, 1).toString(), "PAID");
    }
}
