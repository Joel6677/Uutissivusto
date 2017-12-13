/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class NewsItem extends AbstractPersistable<Long> {

    private String heading;
    private String lead;

    @Lob
    private String body;
    private LocalDateTime publishDate;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Category> categories;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Writer> writers;

    private int count;

    public NewsItem(String heading, String lead, byte[] image, String body, LocalDateTime publishDate) {
        this.heading = heading;
        this.lead = lead;
        this.image = image;
        this.body = body;
        this.publishDate = publishDate;
        this.count = 0;
    }

    public List<Writer> getWriters() {
        if (this.writers == null) {
            this.writers = new ArrayList<>();
        }
        return this.writers;
    }

    public List<Category> getCategories() {
        if (this.categories == null) {
            this.categories = new ArrayList<>();
        }
        return this.categories;
    }

    public void increaseCount() {
        this.count++;
    }
}
