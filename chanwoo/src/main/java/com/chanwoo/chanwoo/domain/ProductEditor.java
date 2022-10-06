package com.chanwoo.chanwoo.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductEditor {
    private final String title;
    private final String content;

    public ProductEditor(String title, String content) {

        this.title = title;
        this.content = content;
    }

    public static ProductEditor.ProductEditorBuilder builder() {
        return new ProductEditor.ProductEditorBuilder();
    }

    public static class ProductEditorBuilder {
        private String title;
        private String content;

        ProductEditorBuilder() {
        }

        public ProductEditor.ProductEditorBuilder title(final String title) {
            if (title != null) {
                this.title = title;
            }
            return this;
        }

        public ProductEditor.ProductEditorBuilder content(final String content) {
            if (content != null) {
                this.content = content;
            }
            return this;
        }

        public ProductEditor build() {
            return new ProductEditor(this.title, this.content);
        }

        public String toString() {
            return "ProductEditor.ProductEditorBuilder(title=" + this.title + ", content=" + this.content + ")";
        }
    }
}
