package wallet_api.dto;

public class AmountDTO {

    private Double amount;

    public AmountDTO() {
    }

    public AmountDTO(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}