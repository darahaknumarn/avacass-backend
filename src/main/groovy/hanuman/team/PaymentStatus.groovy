package hanuman.team

enum PaymentStatus {
    PENDING("Pending"),
    PAID("Paid")
    String status

    PaymentStatus(String status) {
        this.status = status
    }
}
