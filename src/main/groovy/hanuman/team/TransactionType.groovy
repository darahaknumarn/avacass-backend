package hanuman.team

enum TransactionType {
    PURCHASE("Purchase"),
    ADJUST_STOCK("AdjustStock"),
    SALE("Sale")

    String status

    TransactionType(String status) {
        this.status = status
    }
}