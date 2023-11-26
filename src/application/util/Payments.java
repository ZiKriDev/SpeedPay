package application.util;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Payout;

import java.util.HashMap;
import java.util.Map;

public class Payments {

    public void doPayments() {
        // Configure sua chave secreta da API Stripe
        Stripe.apiKey = "sk_test_51OEXhaHKcARfOPFO8e287zClHqWhsM7nSGR0dT3cQVJn0J9VLg5cIuvI3tczMvdtazczUh30kXG7HSoZnfsSxpTG00IFa2qCGo";

        try {
            // Crie um mapa com os parâmetros necessários para o pagamento (payout)
            Map<String, Object> payoutParams = new HashMap<>();
            payoutParams.put("amount", 1000); // Valor em centavos (ex: R$ 10,00)
            payoutParams.put("currency", "brl"); // Moeda
            payoutParams.put("destination", "ba_123456789"); // ID da conta bancária destinatária

            // Crie o pagamento (payout) simulado
            Payout payout = Payout.create(payoutParams);

            // Aqui você pode usar o payout.getId() para rastrear o pagamento, se
            // necessário.

            System.out.println("Pagamento simulado para conta bancária criado com sucesso. ID do pagamento: " + payout.getId());
        } catch (StripeException e) {
            // Lida com exceções da Stripe
            e.printStackTrace();
        }
    }
}
