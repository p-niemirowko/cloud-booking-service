CREATE TYPE booking_status AS ENUM (
    'INITIALIZED',
    'PENDING',
    'CONFIRMED',
    'CANCELED',
    'EXPIRED'
    );

CREATE TABLE bookings
(
    id                   UUID PRIMARY KEY,

    owner_id             UUID           NOT NULL,
    property_id          UUID           NOT NULL,

    payment_id           UUID,

    date_from            DATE           NOT NULL,
    date_to              DATE           NOT NULL,

    status               booking_status NOT NULL,

    payment_redirect_url TEXT,
    payment_expires_at   TIMESTAMPTZ,

    total_price          NUMERIC(12, 2) NOT NULL,
    currency             CHAR(3)        NOT NULL,

    expires_at           TIMESTAMPTZ,
    created_at           TIMESTAMPTZ      NOT NULL DEFAULT now(),
    idempotency_key      TEXT,

    CONSTRAINT booking_date_check CHECK (date_to > date_from),
    CONSTRAINT booking_price_positive CHECK (total_price > 0),
    CONSTRAINT currency_form CHECK (currency ~ '^[A-Z]{3}$')
);

CREATE INDEX idx_bookings_property_dates
    ON bookings (property_id, date_from, date_to);

CREATE INDEX idx_bookings_owner
    ON bookings (owner_id);

CREATE INDEX idx_bookings_payment
    ON bookings (payment_id);

CREATE INDEX idx_bookings_status
    ON bookings (status);

CREATE UNIQUE INDEX ux_booking_idempotency
    ON bookings(idempotency_key)
    WHERE idempotency_key IS NOT NULL;

-- w przyszłości dodać (aby sprawdzić jak to realnie wpływa na szybkość)
-- CREATE INDEX idx_bookings_expiration
--     ON bookings(expires_at)
--     WHERE status = 'PENDING';
