package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.exeption.ResourceNotFoundException;
import pl.azebrow.harvest.model.BalanceChange;
import pl.azebrow.harvest.model.BalanceChangeType;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.Job;
import pl.azebrow.harvest.repository.BalanceChangeRepository;
import pl.azebrow.harvest.request.PaymentRequest;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceChangeRepository balanceRepository;

    private final EmployeeService employeeService;

    public void addJobEarning(Job job) {
        BalanceChange balanceChange = new BalanceChange();
        balanceChange.setEmployee(job.getEmployee());
        balanceChange.setAmount(job.getTotalAmount());
        balanceChange.setType(BalanceChangeType.EARNING);
        balanceRepository.saveAndFlush(balanceChange);
        job.setBalanceChange(balanceChange);
    }

    public void updateJobEarning(Job job) {
        BalanceChange balanceChange = job.getBalanceChange();
        balanceChange.setEmployee(job.getEmployee());
        balanceChange.setAmount(job.getTotalAmount());
        balanceRepository.saveAndFlush(balanceChange);
    }

    public void addPayment(PaymentRequest request) {
        BalanceChange balanceChange = new BalanceChange();
        Employee employee = employeeService.getEmployeeById(request.getEmployeeId());
        balanceChange.setEmployee(employee);
        balanceChange.setAmount(request.getAmount().negate());
        balanceChange.setType(BalanceChangeType.PARTIAL_PAYMENT);
        balanceRepository.save(balanceChange);
    }

    public void updatePayment(PaymentRequest request, Long id) {
        BalanceChange balanceChange = getBalanceChangeById(id);
        if (balanceChange.getType().equals(BalanceChangeType.EARNING)) {
            throw new IllegalArgumentException("Earnings can only be modified by updating Job entities");
        }
        Employee employee = employeeService.getEmployeeById(request.getEmployeeId());
        balanceChange.setEmployee(employee);
        balanceChange.setAmount(request.getAmount().negate());
        balanceRepository.save(balanceChange);
    }

    private BalanceChange getBalanceChangeById(Long id) {
        return balanceRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Balance change with id \"%s\" not found!")
                );
    }

}
