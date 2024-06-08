package softuni.exam.service;

import softuni.exam.models.entity.LibraryMember;

import java.io.IOException;
import java.util.Optional;

// TODO: Implement all methods
public interface LibraryMemberService {

    boolean areImported();

    String readLibraryMembersFileContent() throws IOException;
	
	String importLibraryMembers() throws IOException;

    LibraryMember findById(long id);

    LibraryMember findMemberById(long id);
}
