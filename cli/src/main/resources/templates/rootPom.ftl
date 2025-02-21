<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <!-- Build Parent -->
  <parent>
    <groupId>tools.vitruv</groupId>
    <artifactId>parent</artifactId>
    <version>3.0.7</version>
  </parent>

  <!-- Project Information -->
  <artifactId>${packageName}</artifactId>
  <version>0.1.0-SNAPSHOT</version>
  <packaging>pom</packaging>
  <#noparse>
  <name>Methodologist Template</name>
  <description />

  <!-- Modules -->
  <modules>
    <!-- <module>viewtype</module> -->
    <!-- <module>vsum</module> -->
    <module>model</module>
    <module>consistency</module>
  </modules>

  <properties>
    <vitruv.version>3.1.2</vitruv.version>
  </properties>

  <dependencyManagement>
    <dependencies>
      <!-- Vitruvius dependencies -->
      <dependency>
        <groupId>tools.vitruv</groupId>
        <artifactId>tools.vitruv.change.atomic</artifactId>
        <version>${vitruv.version}</version>
      </dependency>
      <dependency>
        <groupId>tools.vitruv</groupId>
        <artifactId>tools.vitruv.change.composite</artifactId>
        <version>${vitruv.version}</version>
      </dependency>
      <dependency>
        <groupId>tools.vitruv</groupId>
        <artifactId>tools.vitruv.change.interaction</artifactId>
        <version>${vitruv.version}</version>
      </dependency>
      <dependency>
        <groupId>tools.vitruv</groupId>
        <artifactId>tools.vitruv.change.propagation</artifactId>
        <version>${vitruv.version}</version>
      </dependency>
      <dependency>
        <groupId>tools.vitruv</groupId>
        <artifactId>tools.vitruv.change.testutils.integration</artifactId>
        <version>${vitruv.version}</version>
      </dependency>
      <dependency>
        <groupId>tools.vitruv</groupId>
        <artifactId>tools.vitruv.dsls.reactions.runtime</artifactId>
        <version>${vitruv.version}</version>
      </dependency>
      <dependency>
        <groupId>tools.vitruv</groupId>
        <artifactId>tools.vitruv.framework.views</artifactId>
        <version>${vitruv.version}</version>
      </dependency>
      <dependency>
        <groupId>tools.vitruv</groupId>
        <artifactId>tools.vitruv.framework.vsum</artifactId>
        <version>${vitruv.version}</version>
      </dependency>

      <!-- external dependencies-->
      <dependency>
        <groupId>org.eclipse.emf</groupId>
        <artifactId>org.eclipse.emf.common</artifactId>
        <version>2.40.0</version>
      </dependency>
      <dependency>
        <groupId>org.eclipse.emf</groupId>
        <artifactId>org.eclipse.emf.ecore</artifactId>
        <version>2.38.0</version>
      </dependency>
      <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.1</version>
      </dependency>
    </dependencies>
  </dependencyManagement>
</project>
</#noparse>