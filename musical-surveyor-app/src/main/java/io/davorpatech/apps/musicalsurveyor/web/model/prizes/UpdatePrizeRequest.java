package io.davorpatech.apps.musicalsurveyor.web.model.prizes;

import io.davorpatech.fwk.model.BaseValueObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

/**
 * Represents the HTTP request body to update an existing {@code Prize}.
 *
 * <p>Request DTOs are immutable objects. As a DTO, it is a simple
 * POJO that holds data and has no behavior. It is used to transfer
 * data between the client and the server. It is also used to validate
 * the data sent to the server. It is a good practice to use different
 * DTOs for the request and the response. This way, the response DTO
 * can be extended in the future without breaking the client. This
 * is not the case for the request DTO, which should be kept as simple
 * as possible.
 *
 * <p>This is why the {@link CreatePrizeRequest} and the
 * {@link UpdatePrizeRequest} are different classes. They both
 * represent the same data, but the {@link UpdatePrizeRequest} has
 * an additional {@code id} field. This is because the {@code id} is
 * required to update an existing {@code Prize}. The {@code id} is
 * not required to create a new {@code Prize} because the server
 * will generate a new {@code id} for the new {@code Prize}.
 */
@Schema(
    name = "UpdatePrizeRequest",
    description = "Represents the HTTP request body to update an existing Prize."
)
public class UpdatePrizeRequest extends BaseValueObject implements Identifiable<Long> // NOSONAR
{
    @Serial
    private static final long serialVersionUID = -1631242092026103420L;


}
