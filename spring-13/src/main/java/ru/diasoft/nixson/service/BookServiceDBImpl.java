package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceDBImpl implements BookServiceDB {
    private final MutableAclService mutableAclService;
    private final BookRepository bookRepository;

    @Override
    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book update(Long id, Book book) {
        Optional<Book> old = bookRepository.findById(id);
        if (old.isPresent()) {
            Book oldBook = old.get();
            oldBook.setName(book.getName());
            oldBook.setYear(book.getYear());
            oldBook.setDescription(book.getDescription());
            book = oldBook;
        }
        return bookRepository.save(book);

    }

    @Transactional
    @Override
    public Book insert(Book book) {
        bookRepository.save(book);
        final Sid owner = new PrincipalSid(
                SecurityContextHolder.getContext()
                        .getAuthentication());
        final Sid admin = new GrantedAuthoritySid("ROLE_ADMIN");
        final ObjectIdentity oid = new
                ObjectIdentityImpl(Book.class, book.getId());
        final MutableAcl acl = mutableAclService.createAcl(oid);
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ,
                owner, true);
        acl.insertAce(acl.getEntries().size(),
                BasePermission.ADMINISTRATION, admin, true);
        mutableAclService.updateAcl(acl);
        return book;
    }

    @Override
    public Iterable<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
