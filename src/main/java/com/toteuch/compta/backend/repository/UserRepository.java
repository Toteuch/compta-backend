package com.toteuch.compta.backend.repository;

import static com.toteuch.compta.backend.data.compta.tables.TableUser.USER;

import com.toteuch.compta.backend.dto.UserDTO;
import org.jooq.DSLContext;
import org.jooq.Record;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional(Transactional.TxType.SUPPORTS)
public class UserRepository {

    @Inject
    DSLContext ctx;

    public UserDTO create(final UserDTO user) {
        Record r = ctx.newRecord(USER, user);
        return ctx.insertInto(USER).set(r).returning().fetchOne().into(UserDTO.class);
    }

    public void delete(final Long id) {
        ctx.deleteFrom(USER).where(USER.ID.eq(id)).execute();
    }

    public UserDTO update(UserDTO user) {
        Record r = ctx.newRecord(USER, user);
        return ctx.update(USER).set(r).where(USER.ID.eq(user.id)).returning().fetchOne().into(UserDTO.class);
    }

    public UserDTO get(Long id) {
        return ctx.select().from(USER).where(USER.ID.eq(id)).fetchOneInto(UserDTO.class);
    }

    public List<UserDTO> getAll() {
        return ctx.select().from(USER).fetchInto(UserDTO.class);
    }

    public UserDTO getByName(String name) { return ctx.select().from(USER).where(USER.NAME.eq(name)).fetchOneInto(UserDTO.class); }
}
