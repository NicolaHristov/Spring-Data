package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BorrowDto;
import softuni.exam.models.dto.BorrowingRecordRootDto;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.Genre;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BookService;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {
    private static final String BORROW_PATH = "src/main/resources/files/xml/borrowing-records.xml";
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final LibraryMemberRepository libraryMemberRepository;
    private final BookService bookService;
    private final LibraryMemberService libraryMemberService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository, BookRepository bookRepository, LibraryMemberRepository libraryMemberRepository, BookService bookService, LibraryMemberService libraryMemberService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.libraryMemberRepository = libraryMemberRepository;
        this.bookService = bookService;
        this.libraryMemberService = libraryMemberService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return borrowingRecordRepository.count() > 0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return Files.readString(Path.of(BORROW_PATH));
    }
//    @Override
//    public String importBorrowingRecords() throws JAXBException, FileNotFoundException {
//        StringBuilder sb = new StringBuilder();
//
//        xmlParser
//                .fromFile(BORROW_PATH, BorrowingRecordRootDto.class).getBorrowDtoList()
//                .stream()
//                .filter(borrowingRecordSeedDto -> {
//                    boolean isValid = validationUtil.isValid(borrowingRecordSeedDto);
//
//                    LibraryMember libraryMember = libraryMemberService.findMemberById(borrowingRecordSeedDto.getMemberIdDto().getId());
//                    if (libraryMember == null) {
//                        isValid = false;
//                    }
//
////                    Book bookByTitle = bookService.getBookByTitle(borrowingRecordSeedDto.getBook().getTitle());
////                    Book bookByTitle = bookRepository.findByTitle(borrowingRecordSeedDto.getBorrowBookDto().getTitle()).get();
//                    Book bookOpt = bookService.getBookByTitle(borrowingRecordSeedDto.getBorrowBookDto().getTitle());
//
//                    if (bookOpt == null) {
//                        isValid = false;
//                    }
//
//                    sb
//                            .append(isValid
//                                    ? String.format("Successfully imported borrowing record %s - %s",
//                                    borrowingRecordSeedDto.getBorrowBookDto().getTitle(),
//                                    borrowingRecordSeedDto.getBorrowDate())
//                                    : "Invalid borrowing record")
//                            .append(System.lineSeparator());
//
//                    return isValid;
//                })
//                .map(borrowingRecordDto -> {
//                    BorrowingRecord borrowingRecord = modelMapper.map(borrowingRecordDto, BorrowingRecord.class);
//
//                    LibraryMember libraryMember = libraryMemberService.findMemberById(borrowingRecordDto.getMemberIdDto().getId());
//                    Book bookByTitle = bookService.getBookByTitle(borrowingRecordDto.getBorrowBookDto().getTitle());
//
//                    borrowingRecord.setBook(bookByTitle);
//                    borrowingRecord.setLibraryMember(libraryMember);
//
//                    return borrowingRecord;
//                })
//                .forEach(borrowingRecordRepository::save);
//
//
//        return sb.toString();
//    }

//    @Override
//    public String importBorrowingRecords() throws IOException, JAXBException {
//        List<BorrowDto> borrowDtoList = xmlParser.fromFile(BORROW_PATH, BorrowingRecordRootDto.class).getBorrowDtoList();
//        StringBuilder stringBuilder = new StringBuilder();
//        //If a book with the given title doesn't exist in the DB return "Invalid borrowing record".
//        //If a library member with the given id doesn't exist in the DB return "Invalid borrowing record".
//
//        for (BorrowDto borrowDto : borrowDtoList) {
//            boolean isValid = validationUtil.isValid(borrowDto);
//
//            Optional<Book>optionalBook = bookRepository.findByTitle(borrowDto.getBorrowBookDto().getTitle());
//            if(optionalBook.isEmpty()){
//                isValid = false;
//            }
////            Optional<LibraryMember>optionalLibraryMember = libraryMemberRepository.findById(borrowDto.getMemberIdDto().getId());
////            LibraryMember libraryMember = libraryMemberRepository.findById(borrowDto.getMemberIdDto().getId()).orElse(null);
//            LibraryMember libraryMember = libraryMemberService.findMemberById(borrowDto.getMemberIdDto().getId());
//            if(libraryMember == null){
//                isValid = false;
//            }
//
//            stringBuilder.append(isValid ? String.format("Successfully imported borrowing record %s - %s",
//            borrowDto.getBorrowBookDto().getTitle(),borrowDto.getBorrowDate()) : "Invalid borrowing record").append(System.lineSeparator());
//
//
//        }
//        return stringBuilder.toString();
//    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {
        List<BorrowDto> borrowDtoList = xmlParser.fromFile(BORROW_PATH, BorrowingRecordRootDto.class).getBorrowDtoList();

        StringBuilder stringBuilder = new StringBuilder();
        for (BorrowDto borrowDto : borrowDtoList) {
            //•	If a book with the given title doesn't exist in the DB return "Invalid borrowing record".
            //•	If a library member with the given id doesn't exist in the DB return "Invalid borrowing record".
            Optional<Book> isBookExist = bookRepository.findByTitle(borrowDto.getBorrowBookDto().getTitle());
//            Optional<LibraryMember> isMemberExist = libraryMemberRepository.findById(borrowDto.getMemberIdDto().getId());
            LibraryMember libraryMember = libraryMemberService.findById(borrowDto.getMemberIdDto().getId());
            boolean isValid = validationUtil.isValid(borrowDto) && isBookExist.isPresent() && libraryMember !=null;

            stringBuilder.append(isValid ? String.format("Successfully imported borrowing record %s - %s",borrowDto.getBorrowBookDto().getTitle(),
                    borrowDto.getBorrowDate()) : "Invalid borrowing record").append(System.lineSeparator());

            if(isValid){
                BorrowingRecord dto = modelMapper.map(borrowDto, BorrowingRecord.class);
                LibraryMember libraryMember1 = libraryMemberService.findMemberById(borrowDto.getMemberIdDto().getId());
                Book book = bookService.getBookByTitle(dto.getBook().getTitle());

//                if (dto.getLibraryMember() != null) {
//                    Long memberId = dto.getLibraryMember().getId();
//                    // Продължаваш с обработката
//                } else {
//                    // Обработка на случая, когато libraryMember е null
//                    System.out.println("LibraryMember е null за този запис");
//                }

                dto.setLibraryMember(libraryMember1);
                dto.setBook(book);
                borrowingRecordRepository.save(dto);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String exportBorrowingRecords() {
        StringBuilder stringBuilder = new StringBuilder();
        borrowingRecordRepository.findAllByBorrowDateBeforeAndBookGenreOrderByBorrowDateDesc
                (LocalDate.parse("2021-09-10"), Genre.SCIENCE_FICTION.toString()).forEach(borrowingRecord -> {
            stringBuilder.append(String.format("Book title: %s%n" +
                            "*Book author: %s%n" +
                            "**Date borrowed: %s%n" +
                            "***Borrowed by: %s %s%n", borrowingRecord.getBook().getTitle(),
                    borrowingRecord.getBook().getAuthor(), borrowingRecord.getBorrowDate(),
                    borrowingRecord.getLibraryMember().getFirstName(), borrowingRecord.getLibraryMember().getLastName()));
        });
        //"Book title: {bookTitle}
        //"*Book author: {bookAuthor}
        //"**Date borrowed: {dateBorrowed}
        //"***Borrowed by: {firstName} {lastName}
        //. . ."
        return stringBuilder.toString();
    }
}
