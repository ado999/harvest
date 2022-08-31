package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constant.RoleEnum;
import pl.azebrow.harvest.request.PaymentRequest;
import pl.azebrow.harvest.response.PaymentResponse;
import pl.azebrow.harvest.service.PaymentService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/payment")
@Secured({RoleEnum.Constants.STAFF, RoleEnum.Constants.ADMIN})
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    private final ModelMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createPayment(
            @RequestBody PaymentRequest request
    ) {
        paymentService.addPayment(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePayment(
            @PathVariable Long id,
            @RequestBody PaymentRequest request
    ) {
        paymentService.updatePayment(request, id);
    }

    @GetMapping("/{id}")
    public PaymentResponse getPayment(
            @PathVariable Long id
    ) {
        var payment = paymentService.getPaymentById(id);
        return mapper.map(payment, PaymentResponse.class);
    }

    @GetMapping("/employee/{id}")
    public Collection<PaymentResponse> getPaymentByEmployeeId(
            @PathVariable Long id
    ) {
        var payment = paymentService.getPaymentsByEmployeeId(id);
        return payment
                .stream()
                .map(p -> mapper.map(p, PaymentResponse.class))
                .collect(Collectors.toList());
    }

}
