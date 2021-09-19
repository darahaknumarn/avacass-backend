package hanuman.team

enum TransactionType {
    PURCHASE("Purchase"),
    ADJUST_STOCK("AdjustStock"),
    SALE_RETURN("Sale Return")

    String status

    TransactionType(String status) {
        this.status = status
    }
}