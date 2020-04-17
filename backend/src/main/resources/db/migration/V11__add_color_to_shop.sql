-- Add a shop auto color column and a switch to disable it
ALTER TABLE shop
    ADD COLUMN shop_color varchar DEFAULT "#FFFFFF";
    ADD COLUMN auto_color_enabled boolean NOT NULL DEFAULT false;

