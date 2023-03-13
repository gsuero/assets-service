package com.collibra.gsuero.assets.model.domain;

import jakarta.persistence.*;
import jdk.jfr.Category;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "assets", schema = "public")
public class AssetDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private AssetDao parent;

    @OneToMany(mappedBy = "parent")
    private Set<AssetDao> assets = new HashSet<>();

    private boolean promoted = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AssetDao getParent() {
        return parent;
    }

    public void setParent(AssetDao parent) {
        this.parent = parent;
    }

    public Set<AssetDao> getAssets() {
        return assets;
    }

    public void setAssets(Set<AssetDao> assets) {
        this.assets = assets;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssetDao asset = (AssetDao) o;
        return id == asset.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
