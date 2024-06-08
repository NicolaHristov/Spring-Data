package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BookDto;
import softuni.exam.models.entity.Book;
import softuni.exam.repository.BookRepository;
import softuni.exam.service.BookService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {


    private static final String BOOK_PATH = "src/main/resources/files/json/books.json";
    private final BookRepository bookRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public BookServiceImpl(BookRepository bookRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.bookRepository = bookRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return bookRepository.count() > 0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return Files.readString(Path.of(BOOK_PATH));
    }

    @Override
    public String importBooks() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BookDto[] bookDtos = gson.fromJson(readBooksFromFile(), BookDto[].class);
        for (BookDto bookDto : bookDtos) {
            Optional<Book> isBookExist = bookRepository.findByTitle(bookDto.getTitle());
            boolean isValid = validationUtil.isValid(bookDto) && isBookExist.isEmpty();

            stringBuilder.append(isValid ? String.format("Successfully imported book %s - %s"
                    ,bookDto.getAuthor(),bookDto.getTitle()):
                    "Invalid book").append(System.lineSeparator());

            if(isValid){
                Book book = modelMapper.map(bookDto, Book.class);
                bookRepository.save(book);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public Optional<Book> getBookById(Long bookid) {
        return bookRepository.findById(bookid);
    }

    @Override
    public Book getBookByTitle(String title) {
        return bookRepository.findByTitle(title).orElse(null);
    }
}
