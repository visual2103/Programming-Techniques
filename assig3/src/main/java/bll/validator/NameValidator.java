package bll.validator;

import model.Client;

public class NameValidator implements Validators<Client> {

    @Override
    public void validate(Client client) {
        String name = client.getNume();

        if (containsDigits(name)) {
            throw new IllegalArgumentException("Numele nu poate conține cifre.");
        }

        if (!startsWithUppercase(name)) {
            throw new IllegalArgumentException("Numele trebuie să înceapă cu o literă mare.");
        }
    }

    private boolean containsDigits(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean startsWithUppercase(String str) {
        return Character.isUpperCase(str.charAt(0));
    }
}
