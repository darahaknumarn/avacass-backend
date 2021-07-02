package hanuman.team

enum PaymentStatus {
    PENDING("Pending")

    String status

    PaymentStatus(String status) {
        this.status = status
    }
}