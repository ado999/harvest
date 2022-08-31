package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.exeption.ResourceNotFoundException;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.Payment;
import pl.azebrow.harvest.repository.PaymentRepository;
import pl.azebrow.harvest.request.PaymentRequest;
import pl.azebrow.harvest.utils.CallerFacade;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final EmployeeService employeeService;

    private final CallerFacade callerFacade;

    public void addPayment(PaymentRequest request) {
        addOrUpdatePayment(new Payment(), request);
    }

    public void updatePayment(PaymentRequest request, Long id) {
        Payment payment = getPaymentById(id);
        addOrUpdatePayment(payment, request);
    }

    private void addOrUpdatePayment(Payment payment, PaymentRequest request) {
        Employee employee = employeeService.getEmployeeById(request.getEmployeeId());
        payment.setEmployee(employee);
        payment.setAmount(request.getAmount().negate());
        payment.setPayer(callerFacade.getCaller());
        paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Payment with id \"%s\" not found!")
                );
    }

    public Collection<Payment> getPaymentsByEmployeeId(Long employeeId) {
        var employee = employeeService.getEmployeeById(employeeId);
        return paymentRepository
                .findByEmployee(employee);
    }

    public Collection<Payment> findPayments(Specification<Payment> specs) {
        return paymentRepository.findAll(specs);
    }
}
