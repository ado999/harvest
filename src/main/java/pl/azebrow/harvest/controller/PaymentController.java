package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.enums.RoleEnum;
import pl.azebrow.harvest.request.PaymentRequest;
import pl.azebrow.harvest.response.PaymentResponse;
import pl.azebrow.harvest.service.PaymentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/payment")
@Secured({RoleEnum.Constants.STAFF, RoleEnum.Constants.ADMIN})
@RequiredArgsConstructor
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    private final ModelMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPayment(
            @Valid @RequestBody PaymentRequest request
    ) {
        paymentService.addPayment(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePayment(
            @NotNull @Min(1) @PathVariable Long id,
            @Valid @RequestBody PaymentRequest request
    ) {
        paymentService.updatePayment(request, id);
    }

    @GetMapping("/{id}")
    public PaymentResponse getPayment(
            @NotNull @Min(1) @PathVariable Long id
    ) {
        var payment = paymentService.getPaymentById(id);
        return mapper.map(payment, PaymentResponse.class);
    }

    @GetMapping("/employee/{id}")
    public Page<PaymentResponse> getPaymentsByEmployeeId(
            @NotNull @Min(1) @PathVariable Long id,
            @RequestParam(required = false) Pageable pageable
    ) {
        var payment = paymentService.getPaymentsByEmployeeId(id, pageable);
        return payment
                .map(p -> mapper.map(p, PaymentResponse.class));
    }

}
