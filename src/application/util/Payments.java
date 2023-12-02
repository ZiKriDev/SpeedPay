package application.util;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Payout;

import java.util.HashMap;
import java.util.Map;

public class Payments {

    public void doPayments() {
        Stripe.apiKey = "sk_test_51ODogtFdq6idqxEosFWWNEqlz5z4VRz7DZDRkih0olgbfhxrsqkHQqb3JgwJaBuh9tG18lTTu51Qw4yrFK4Kwttb00JquhFLSG";

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
