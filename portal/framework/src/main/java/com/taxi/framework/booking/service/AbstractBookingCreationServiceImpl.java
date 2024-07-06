package com.taxi.framework.booking.service;

import com.taxi.framework.booking.dto.BaseBookedRequestDTO;
import com.taxi.framework.booking.dto.BaseBookingRequestDTO;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBookingCreationServiceImpl<B extends BaseBookingRequestDTO, R extends BaseBookedRequestDTO> implements BookingCreationService<B, R> {

    private Map<Long, R> assignedDriversMap = new HashMap<>();

    private final String dispatchEndpoint;
    private final String promotionServiceUrl = "http://localhost:8081/api/promotion";

    protected AbstractBookingCreationServiceImpl(String dispatchEndpoint) {
        this.dispatchEndpoint = dispatchEndpoint;
    }

    @Override
    @Transactional
    public String createBooking(BaseBookingRequestDTO bookingRequestDTO) {
        // Call the dispatch service to find a driver
        RestTemplate restTemplate = new RestTemplate();
        String findDriverUrl = dispatchEndpoint + "/find/driver";
        ResponseEntity<Void> findDriverResponse = restTemplate.exchange(
                findDriverUrl,
                HttpMethod.POST,
                new HttpEntity<>(bookingRequestDTO),
                Void.class
        );

        if (findDriverResponse.getStatusCode() == HttpStatus.OK) {
            // If driver found, proceed with booking creation
            R bookedRequest = createBookedRequestDTO();
            bookedRequest.setMessage(R.MessageEnum.LOOKING_FOR_A_DRIVER);
            assignedDriversMap.put(bookingRequestDTO.getUserId(), bookedRequest);

            // Apply promotion if promotion code is provided
            if (bookingRequestDTO.getPromotionCode() != null) {
                applyPromotionToBooking(bookingRequestDTO, bookingRequestDTO.getPromotionCode());
            }

            return "Booking request successful.";
        } else {
            // Handle the case where finding a driver fails
            return "Booking request failed to find a driver.";
        }
    }

    private void applyPromotionToBooking(BaseBookingRequestDTO bookingDTO, String promotionCode) {
        // Prepare the request to the PromotionService to apply the promotion
        String applyPromotionUrl = promotionServiceUrl + "/apply/" + promotionCode;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Double> responseEntity = restTemplate.postForEntity(
                applyPromotionUrl,
                bookingDTO,
                Double.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Double discountedPriceDouble = responseEntity.getBody();
            assert discountedPriceDouble != null;
            float discountedPriceFloat = discountedPriceDouble.floatValue();
            bookingDTO.setPrice(discountedPriceFloat);
        } else {
            throw new RuntimeException("Failed to apply promotion");
        }
    }

    @Override
    public R booked(R bookedRequestDTO, long userId) {

        bookedRequestDTO.setMessage(BaseBookedRequestDTO.MessageEnum.DRIVER_ON_THE_WAY);
        assignedDriversMap.put(userId, bookedRequestDTO);

        return bookedRequestDTO;
    }

    @Override
    public String bookingNextState(long userId) {

        R bookedRequestDTO = assignedDriversMap.get(userId);
        bookedRequestDTO.setMessage(bookedRequestDTO.getMessage().getNextState());

        assignedDriversMap.put(userId, bookedRequestDTO);

        return bookedRequestDTO.getMessage().getMessage();
    }

    @Override
    public R refresh(long userId){

        R bookedRequestDTO = assignedDriversMap.get(userId);

        if (bookedRequestDTO != null && bookedRequestDTO.getMessage() == BaseBookedRequestDTO.MessageEnum.REACHED_DESTINATION) {
            assignedDriversMap.remove(userId);
        }

        return bookedRequestDTO;
    }

    protected abstract R createBookedRequestDTO();
}
