package hanuman.team

enum OrderStatus {
    Pending ("PENDING"),
    Accepted("ACCEPTED"),
    Delivered ("DELIVERED"),
    Paid ("PAID"),
    Rejected("REJECTED")

    String desc
    OrderStatus(String desc) {
        this.desc = desc
    }
}