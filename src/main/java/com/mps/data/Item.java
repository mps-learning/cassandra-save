package com.mps.data;

import lombok.Data;

/**
 * @author manvendrasingh
 * @since 2023-March-17
 * <p>
 * </p>
 **/
@Data
public class Item {
    private int id;
    private int  albumId;
    private String title;
    private String url;
    private String  thumbnailUrl;
}
