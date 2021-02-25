package net.herospvp.heroscore.objects;

public enum Permission {

    ADMIN("herospvp.admin");

    private final String string;

    Permission(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

}
