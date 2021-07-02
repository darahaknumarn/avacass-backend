package hanuman.team

enum TransactionType {
    PURCHASE("Purchase"),
    ADJUST_STOCK("AdjustStock")

    String status

    TransactionType(String status) {
        this.status = status
    }
}