CREATE TABLE ACCESS_LOG_LINE (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    TSTAMP       TIMESTAMP                NOT NULL,
    IP           VARCHAR(16)              NOT NULL,
    HTTP_REQUEST VARCHAR(512)             NOT NULL,
    HTTP_CODE    INT                      NOT NULL,
    USER_AGENT   VARCHAR(1024)            NOT NULL
);

CREATE INDEX IDX_ALL_TIMESTAMP ON ACCESS_LOG_LINE (TSTAMP);
CREATE INDEX IDX_ALL_IP        ON ACCESS_LOG_LINE (IP);


-- --------- --
--           --
-- Query 1   --
--           --
-- --------- --
--
-- SELECT DISTINCT
--       ALL.IP
--   FROM
--       ACCESS_LOG_LINE ALL
--   WHERE
--       ALL.TSTAMP BETWEEN ?2 AND ?3
--   GROUP BY
--       ALL.IP
--   HAVING COUNT(ALL.HTTP_REQUEST) > ?1
--

-- --------- --
--           --
-- Query 2   --
--           --
-- --------- --
--
-- SELECT DISTINCT
--       ALL.HTTP_REQUEST
--   FROM
--       ACCESS_LOG_LINE ALL
--   WHERE
--       ALL.IP = ?1
--