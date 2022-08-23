package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constant.RoleEnum;
import pl.azebrow.harvest.request.PaymentRequest;
import pl.azebrow.harvest.service.BalanceService;

@RestController
@RequestMapping("/api/v1/balance")
@Secured({RoleEnum.Constants.STAFF, RoleEnum.Constants.ADMIN})
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createPayment(
            @RequestBody PaymentRequest request
    ) {
        balanceService.addPayment(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePayment(
            @PathVariable Long id,
            @RequestBody PaymentRequest request
    ) {
        balanceService.updatePayment(request, id);
    }

}
