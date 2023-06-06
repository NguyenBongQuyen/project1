package jmaster.io.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchTicketDTO;
import jmaster.io.demo.dto.TicketDTO;
import jmaster.io.demo.entity.Ticket;
import jmaster.io.demo.repository.TicketRepo;

public interface TicketService {
	void create(TicketDTO ticketDTO);

	void update(TicketDTO ticketDTO);

	void delete(int id);

	List<TicketDTO> getAll();

	TicketDTO getById(int id);

	PageDTO<List<TicketDTO>> search(SearchTicketDTO searchDTO);
}

@Service
class TicketServiceImpl implements TicketService {
	@Autowired
	TicketRepo ticketRepo;

	@Override
	public void create(TicketDTO ticketDTO) {
		Ticket ticket = new ModelMapper().map(ticketDTO, Ticket.class);
		ticketRepo.save(ticket);
	}

	@Override
	public void update(TicketDTO ticketDTO) {
		// check
		Ticket ticket = ticketRepo.findById(ticketDTO.getId()).orElse(null);
		if (ticket != null) {
			ticket.setClientName(ticketDTO.getClientName());
			ticket.setContent(ticketDTO.getContent());
			ticket.setClientPhone(ticketDTO.getClientPhone());
			ticket.setStatus(ticketDTO.isStatus());

			// save entity
			ticketRepo.save(ticket);
		}
	}

	@Override
	public void delete(int id) {
		ticketRepo.deleteById(id);
	}

	private TicketDTO convert(Ticket ticket) {
		return new ModelMapper().map(ticket, TicketDTO.class);
	}

	@Override
	public TicketDTO getById(int id) {
		// Optional
		Ticket ticket = ticketRepo.findById(id).orElse(null);

		if (ticket != null)
			return convert(ticket);

		return null;
	}

	@Override
	public PageDTO<List<TicketDTO>> search(SearchTicketDTO searchDTO) {
		Sort sortBy = Sort.by("createdAt").ascending();

		if (StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}
		if (searchDTO.getCurrentPage() == null)
			searchDTO.setCurrentPage(0);
		if (searchDTO.getSize() == null)
			searchDTO.setSize(3);

		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);

		Page<Ticket> page = ticketRepo.findAll(pageRequest);
		if (searchDTO.getStart() != null && searchDTO.getEnd() != null) {
			page = ticketRepo.searchByDate(searchDTO.getStart(), searchDTO.getEnd(), pageRequest);
		} else if (searchDTO.getDepartmentId() != null) {
			page = ticketRepo.searchByDepartmentId(searchDTO.getDepartmentId(), pageRequest);
		} else if (StringUtils.hasText(searchDTO.getKeyword())) {
			page = ticketRepo.searchByName(searchDTO.getKeyword(), pageRequest);
		}

		PageDTO<List<TicketDTO>> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());

		List<TicketDTO> ticketDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());

		pageDTO.setData(ticketDTOs);

		return pageDTO;
	}

	@Override
	public List<TicketDTO> getAll() {
		List<Ticket> ticketList = ticketRepo.findAll();
		return ticketList.stream().map(u -> convert(u)).collect(Collectors.toList());
	}

}
