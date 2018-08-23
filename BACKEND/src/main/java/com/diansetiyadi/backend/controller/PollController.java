package com.diansetiyadi.backend.controller;


import com.diansetiyadi.backend.model.Poll;
import com.diansetiyadi.backend.payload.request.PollRequest;
import com.diansetiyadi.backend.payload.request.VoteRequest;
import com.diansetiyadi.backend.payload.response.ApiResponse;
import com.diansetiyadi.backend.payload.response.PagedResponse;
import com.diansetiyadi.backend.payload.response.PollResponse;
import com.diansetiyadi.backend.repository.PollRepository;
import com.diansetiyadi.backend.repository.UserRepository;
import com.diansetiyadi.backend.repository.VoteRepository;
import com.diansetiyadi.backend.security.CurrentUser;
import com.diansetiyadi.backend.security.UserPrincipal;
import com.diansetiyadi.backend.service.PollService;
import com.diansetiyadi.backend.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PollService pollService;

    private static final Logger logger = LoggerFactory.getLogger(PollController.class);

    @GetMapping
    public PagedResponse<PollResponse> getPolls(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value="page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER)int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE)int size) {
        return pollService.getAllPolls(currentUser, page, size);
    }
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createPoll(@Valid @RequestBody PollRequest pollRequest) {
        Poll poll = pollService.createPoll(pollRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{pollId}").buildAndExpand(poll.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true,"Poll Created Successfully!"));
    }

    @GetMapping("/{pollId}")
    public PollResponse getPollById(@CurrentUser UserPrincipal currentUser,
                                    @PathVariable Long pollId) {
        return pollService.getPollById(pollId, currentUser);
    }

    @PostMapping("/{pollId}/votes")
    @PreAuthorize("hasRole('USER')")
    public PollResponse castVote(@CurrentUser UserPrincipal currentUser, @PathVariable Long pollId,
                                    @Valid @RequestBody VoteRequest voteRequest) {
        return pollService.castVoteAndGetUpdatePoll(pollId, voteRequest, currentUser);
    }


}
