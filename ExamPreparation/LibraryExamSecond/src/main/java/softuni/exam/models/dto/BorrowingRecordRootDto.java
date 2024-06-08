package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "borrowing_records")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordRootDto {




    @XmlElement(name = "borrowing_record")
    private List<BorrowDto> borrowDtoList;

    public List<BorrowDto> getBorrowDtoList() {
        return borrowDtoList;
    }

    public void setBorrowDtoList(List<BorrowDto> borrowDtoList) {
        this.borrowDtoList = borrowDtoList;
    }
}
