package br.ufrn.imd.cbm.enums;

public enum DeliveryState {
    ORDER_REQUESTED(0),
    ORDER_CONFIRMED(1),
    ORDER_READY(2),
    ORDER_FINISHED(3);

    private int value;

    DeliveryState(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        if (value <= this.value || value >= 3) {
            return;
        }
        this.value = value;
    }

    public static DeliveryState fromValue(int value) {
        for (DeliveryState state : DeliveryState.values()) {
            if (state.getValue() == value) {
                return state;
            }
        }
        throw new IllegalArgumentException("Invalid deliveryState: " + value);
    }


}
