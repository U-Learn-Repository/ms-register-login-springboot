

DROP TABLE IF EXISTS "labs"."public"."user_role";
DROP TABLE IF EXISTS "labs"."public"."user";
DROP TABLE IF EXISTS "labs"."public"."role";


-- ---------------------------- --
-- Table "labs"."public"."user" --
-- ---------------------------- --
CREATE TABLE IF NOT EXISTS "labs"."public"."user"
(
    "user_id"  SERIAL       NOT NULL,
    "username" VARCHAR(10)  NOT NULL,
    "password" VARCHAR(256) NOT NULL,
    "names"    VARCHAR(100) NOT NULL,
    "surnames" VARCHAR(100) NOT NULL,
    PRIMARY KEY ("user_id")
);

-- ---------------------------- --
-- Table "labs"."public"."role" --
-- ---------------------------- --
CREATE TABLE IF NOT EXISTS "labs"."public"."role"
(
    "role_id"   INTEGER     NOT NULL,
    "role_name" VARCHAR(20) NOT NULL,
    PRIMARY KEY ("role_id")
);


-- --------------------------------- --
-- Table "labs"."public"."user_role" --
-- --------------------------------- --
CREATE TABLE IF NOT EXISTS "labs"."public"."user_role"
(
    "user_id" INT NOT NULL,
    "role_id" INT NOT NULL,
    PRIMARY KEY ("user_id", "role_id"),
    CONSTRAINT "fk_user_id"
        FOREIGN KEY ("user_id")
            REFERENCES "labs"."public"."user" ("user_id")
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT "fk_role_id"
        FOREIGN KEY ("role_id")
            REFERENCES "labs"."public"."role" ("role_id")
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
CREATE INDEX "idx_user_role_user_id" ON "labs"."public"."user" ("user_id" ASC);
CREATE INDEX "idx_user_role_role_id" ON "labs"."public"."role" ("role_id" ASC);



-- ------------------------------------------------------------------------------------------------------------------ --
--                                                   INSERTION DATA                                                   --
-- ------------------------------------------------------------------------------------------------------------------ --

-- --------- --
-- Role data --
-- --------- --

INSERT INTO "labs"."public"."role" (role_id, role_name)
    VALUES (1, 'Estudiante'),
           (2, 'Profesor');

-- ----------- --
-- Period data --
-- ----------- --
