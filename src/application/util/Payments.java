package application.util;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import application.entities.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Payments {

    private String message;
    private String paidEmployees;

    public void doPayments(List<Employee> selectedEmployees) {

        Stripe.apiKey = "sk_test_51OEXhaHKcARfOPFO8e287zClHqWhsM7nSGR0dT3cQVJn0J9VLg5cIuvI3tczMvdtazczUh30kXG7HSoZnfsSxpTG00IFa2qCGo";

        for (Employee emp : selectedEmployees) {
            String customerId = emp.getIdAccount();

            double s = emp.getSalary() * 100;
            int salary = (int) s;

            Map<String, Object> params = new HashMap<>();
            params.put("amount", salary); // Valor em centavos 
            params.put("currency", "brl");
            params.put("payment_method", "pm_card_visa"); // Cartão de teste
            params.put("customer", customerId); // ID da conta
            params.put("confirmation_method", "manual");

            try {
                PaymentIntent paymentIntent = PaymentIntent.create(params);

                String paymentStatus = paymentIntent.getStatus();   
                
                if (paymentStatus.equals("requires_confirmation")) {
                    message = "Pagamento realizado com sucesso!";
                } else {
                    message = "Erro ao executar um pagamento! Verifique o painel da conta Stripe!";
                }
            } catch (StripeException e) {
                e.printStackTrace();
            }
        }
    }

    public String getMessage() {
        return message;
    }

    public String getPaidEmployees() {
        return paidEmployees;
    }
}

