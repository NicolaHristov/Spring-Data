package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.LibraryDto;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {

    private static final String LIB_PATH ="src/main/resources/files/json/library-members.json";
    private final LibraryMemberRepository libraryMemberRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return Files.readString(Path.of(LIB_PATH));
    }

    @Override
    public String importLibraryMembers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        LibraryDto[] libraryDtos = gson.fromJson(readLibraryMembersFileContent(), LibraryDto[].class);
        for (LibraryDto libraryDto : libraryDtos) {
            Optional<LibraryMember>isLibraryNumberExist = libraryMemberRepository.findByPhoneNumber(libraryDto.getPhoneNumber());
            boolean isValid = validationUtil.isValid(libraryDto) && isLibraryNumberExist.isEmpty();


            stringBuilder.append(isValid ? String.format("Successfully imported library member %s - %s"
                    ,libraryDto.getFirstName(),libraryDto.getLastName()) :
                    "Invalid library member").append(System.lineSeparator());

            if (isValid){
                LibraryMember dto = modelMapper.map(libraryDto, LibraryMember.class);
                libraryMemberRepository.save(dto);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public LibraryMember findById(long id) {
        return libraryMemberRepository.findById(id).orElse(null);
    }

    @Override
    public LibraryMember findMemberById(long id) {
        return libraryMemberRepository.findById(id).orElse(null);
    }
}
