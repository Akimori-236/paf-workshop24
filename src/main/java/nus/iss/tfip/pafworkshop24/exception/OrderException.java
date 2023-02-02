package nus.iss.tfip.pafworkshop24.exception;

public class OrderException extends RuntimeException {

    public OrderException() {
        super();
    }

    public OrderException(String errMsg) {
        super(errMsg);
    }
}
