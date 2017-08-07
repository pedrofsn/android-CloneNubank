package br.pedrofsn.clonenubank.utils;

import android.util.Log;

import java.text.NumberFormat;

import br.pedrofsn.clonenubank.App;

/**
 * Created by pedrofsn on 04/01/2016.
 */
public class Utils {

    public static final String MONTH_AND_YEAR = "MMM YY";
    public static final String MONTH_AND_DAY = "dd MMM";
    public static final int INVALID_VALUE = -1;
    public static final String EMPTY_STRING = "";
    private static final String MOEDA = "R$";
    public static boolean IS_MOCK_OFFLINE = true;

    public static String aplicarMoeda(String string) {
        if (string != null)
            return String.format(string, MOEDA);
        else
            return MOEDA;
    }

    public static Float convertToFloat(int valor) {
        Float value = 0F;

        if (valor != 0) {
            //Sem os dois últimos dígitos
            String withoutLastTwoChar = String.valueOf(valor).substring(0, String.valueOf(valor).length() - 2);

            // Os dois últimos dígitos
            String lastTwoChar = String.valueOf(valor).substring(String.valueOf(valor).length() - 2, String.valueOf(valor).length());

            // Result
            String result = withoutLastTwoChar + "." + lastTwoChar;

            value = Float.valueOf(result);
        }

        return value;
    }

    public static String valorSemMoeda(Float entrada) {
        String valor = NumberFormat.getCurrencyInstance().format(entrada);
        valor = valor.replace("(", "-");
        valor = valor.replace(")", "");
        valor = valor.replace(NumberFormat.getCurrencyInstance().getCurrency().getSymbol(), "");
        return valor;
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static void log(String message) {
        if (!Utils.isNull(message))
            Log.i(App.TAG, message);
    }
}
