package softuni.exam.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowDto {

    @XmlElement(name = "borrow_date")
    private String borrowDate;
    @XmlElement(name = "return_date")
    private String returnDate;
    @XmlElement(name = "book")
    @NotNull
    private BorrowBookDto borrowBookDto;
    @XmlElement(name = "member")
    @NotNull
    private MemberIdDto memberIdDto;

    @XmlElement(name = "remarks")
    @Size(min = 3,max = 100)
    private String remarks;

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public BorrowBookDto getBorrowBookDto() {
        return borrowBookDto;
    }

    public void setBorrowBookDto(BorrowBookDto borrowBookDto) {
        this.borrowBookDto = borrowBookDto;
    }

    public MemberIdDto getMemberIdDto() {
        return memberIdDto;
    }

    public void setMemberIdDto(MemberIdDto memberIdDto) {
        this.memberIdDto = memberIdDto;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
