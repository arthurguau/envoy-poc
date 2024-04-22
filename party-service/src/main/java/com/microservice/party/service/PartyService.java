package com.microservice.party.service;

import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.party.dto.EmailChangeDTO;
import com.microservice.party.dto.EnrollPartyDTO;
import com.microservice.party.dto.PartyDTO;

/**
 * Service interface that masks the caller from the implementation that fetches / acts on PartyDTO
 * related data.
 *
 * @author Arthur
 */
public interface PartyService {

    /**
     * Gets the Party Details for the given PartyId.
     *
     * @param partyId
     * @return PartyDTO
     */
    PartyDTO getParty(Integer partyId) throws Exception;

    /**
     * Enrolls the Party.
     *
     * @param party
     * @return PartyDTO
     */
    PartyDTO enrollParty(EnrollPartyDTO party) throws Exception;

    /**
     * Updates the Party Email for the given partyId.
     *
     * @param partyId
     * @param partyEmail
     * @return PartyDTO
     */
    PartyDTO updateParty(Integer partyId, EnrollPartyDTO party) throws Exception;
    
    /**
     * 
     * @param partyId
     * @throws Exception
     */
    void deleteParty(@PathVariable Integer partyId) throws Exception;
}
