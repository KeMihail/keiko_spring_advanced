package com.epam.keikom.service.impl;


import com.epam.keikom.dao.domain.Ticket;
import com.epam.keikom.dao.exceptions.UnknownIdentifierException;
import com.epam.keikom.dao.jdbc.ITicketDao;
import com.epam.keikom.service.ITicketService;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collection;

@Service
public class TicketService implements ITicketService {

    @Resource(name = "ticketDao")
    private ITicketDao ticketDao;

    @Override
    public Ticket save(@Nonnull Ticket ticket) {

        if (ticket.getId() != null) {
            ticketDao.update(ticket);
        } else {
            ticket.setId(ticketDao.save(ticket).getId());
        }
        return ticket;
    }

    @Override
    public void remove(@Nonnull Ticket ticket) {
        ticketDao.remove(ticket);
    }

    @Override
    public Ticket getById(@Nonnull Long id) throws UnknownIdentifierException {

        final Ticket ticket = ticketDao.getById(id);

        if (ticket == null){
            throw new UnknownIdentifierException("Ticket with id: " + id +" not found!");
        }
        return ticket;

    }

    @Nonnull
    @Override
    public Collection<Ticket> getAll() {
        return ticketDao.getAll();
    }
}
