package com.pm.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpcClient {

    private final BillingServiceGrpc.BillingServiceBlockingStub billingServiceBlockingStub;

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort
    ) {
        log.info("Connecting to Billing Service GRPC at {}:{}", serverAddress, serverPort);
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(serverAddress,
                serverPort).usePlaintext().build();
        this.billingServiceBlockingStub = BillingServiceGrpc.newBlockingStub(managedChannel);
    }

    public BillingResponse createBillingAccount(String patientId, String name, String email) {
        BillingRequest billingRequest = BillingRequest.newBuilder()
                .setPatientId(patientId).setName(name).setEmail(email).build();
        BillingResponse billingResponse = billingServiceBlockingStub.createBillingAccount(billingRequest);
        log.info("Received response from Billing Service via GRPC: {}", billingResponse);
        return billingResponse;
    }
}
