package hanuman.team

enum InvoiceStatus {
    Draft, // default status
    Unpaid, // when customer submit it will show as unpaid invoice
    Paid, // when customer completely paid 100% then will show as Paid
    PartialPaid // customer not pay 100% of total invoice amount but paid some of amount.
}
