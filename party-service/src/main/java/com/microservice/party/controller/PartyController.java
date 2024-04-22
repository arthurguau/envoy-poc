package com.microservice.party.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.party.dto.EmailChangeDTO;
import com.microservice.party.dto.EnrollPartyDTO;
import com.microservice.party.dto.PartyDTO;
import com.microservice.party.service.PartyService;

import lombok.extern.slf4j.Slf4j;

/**
 * This spring controller handles all the API calls made on the end point.
 * <p>
 * - /parties/{partyId}: Gets the Party Details for the given PartyId.
 * <p/>
 *
 * @author Songiqng Gu
 */
@Slf4j
@RestController
public class PartyController {

    /**
     * Handle to the service.
     */
    private final PartyService partyService;

    /**
     * Autowired constructor.
     *
     * @param partyService
     */
    @Autowired
    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    /**
     * Gets the Party Details for the given partyId.
     *
     * @param partyId
     * @return PartyDTO
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/parties/{partyId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PartyDTO getParty(@RequestHeader("Authorisation") String jwt, 
    		@RequestHeader("Authorization") String token, @PathVariable Integer partyId) throws Exception {
    	
    	log.info("==> incoming jwt token: " + jwt);
    	log.info("==> incoming token: " + token);
    	
        return this.partyService.getParty(partyId);
    }

    /**
     * Enrolls the Party.
     *
     * @param party
     * @return PartyDTO
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/parties", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PartyDTO enrollParty(@RequestHeader("Authorisation") String jwt, 
    		@RequestHeader("Authorization") String token, @RequestBody EnrollPartyDTO party) throws Exception {
    	
    	log.info("==> incoming jwt token: " + jwt);
    	log.info("==> incoming token: " + token);
    	
        return this.partyService.enrollParty(party);
    }

    /**
     * Updates the party for the given partyId.
     *
     * @param partyId
     * @param partyEmail
     * @return PartyDTO
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/parties/{partyId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PartyDTO updateParty(@RequestHeader("Authorisation") String jwt, 
    		@RequestHeader("Authorization") String token, @PathVariable Integer partyId, @RequestBody EnrollPartyDTO partyDTO) throws Exception {
        return this.partyService.updateParty(partyId, partyDTO);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/parties/{partyId}")
    public void deleteParty(@RequestHeader("Authorisation") String jwt, 
    		@RequestHeader("Authorization") String token, @PathVariable Integer partyId)  throws Exception  {
    	this.partyService.deleteParty(partyId);
    }
}
