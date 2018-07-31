/*
 * DRACOON
 * REST Web Services for DRACOON<br>Version: 4.8.0-LTS  - built at: 2018-05-03 15:44:37<br><br><a title='Developer Information' href='https://developer.dracoon.com'>Developer Information</a>&emsp;&emsp;<a title='Get SDKs on GitHub' href='https://github.com/dracoon'>Get SDKs on GitHub</a>
 *
 * OpenAPI spec version: 4.8.0-LTS
 * Contact: develop@dracoon.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package ch.cyberduck.core.sds.io.swagger.client.model;

/*
 * Copyright (c) 2002-2018 iterate GmbH. All rights reserved.
 * https://cyberduck.io/
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * NodePermissions
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-05-23T09:31:14.222+02:00")
public class NodePermissions {
  @JsonProperty("manage")
  private Boolean manage = null;

  @JsonProperty("read")
  private Boolean read = null;

  @JsonProperty("create")
  private Boolean create = null;

  @JsonProperty("change")
  private Boolean change = null;

  @JsonProperty("delete")
  private Boolean delete = null;

  @JsonProperty("manageDownloadShare")
  private Boolean manageDownloadShare = null;

  @JsonProperty("manageUploadShare")
  private Boolean manageUploadShare = null;

  @JsonProperty("readRecycleBin")
  private Boolean readRecycleBin = null;

  @JsonProperty("restoreRecycleBin")
  private Boolean restoreRecycleBin = null;

  @JsonProperty("deleteRecycleBin")
  private Boolean deleteRecycleBin = null;

  public NodePermissions manage(Boolean manage) {
    this.manage = manage;
    return this;
  }

   /**
   * User / Group may grant all of the above permissions to other users and groups independently, may update room metadata and create / update / delete subordinary rooms, has all permissions.
   * @return manage
  **/
  @ApiModelProperty(example = "false", required = true, value = "User / Group may grant all of the above permissions to other users and groups independently, may update room metadata and create / update / delete subordinary rooms, has all permissions.")
  public Boolean getManage() {
    return manage;
  }

  public void setManage(Boolean manage) {
    this.manage = manage;
  }

  public NodePermissions read(Boolean read) {
    this.read = read;
    return this;
  }

   /**
   * User / Group may see all rooms, files and folders in the room and download everything, copy files from this room.
   * @return read
  **/
  @ApiModelProperty(example = "false", required = true, value = "User / Group may see all rooms, files and folders in the room and download everything, copy files from this room.")
  public Boolean getRead() {
    return read;
  }

  public void setRead(Boolean read) {
    this.read = read;
  }

  public NodePermissions create(Boolean create) {
    this.create = create;
    return this;
  }

   /**
   * User / Group may upload files, create folders and copy / move files to this room, overwriting is not possible.
   * @return create
  **/
  @ApiModelProperty(example = "false", required = true, value = "User / Group may upload files, create folders and copy / move files to this room, overwriting is not possible.")
  public Boolean getCreate() {
    return create;
  }

  public void setCreate(Boolean create) {
    this.create = create;
  }

  public NodePermissions change(Boolean change) {
    this.change = change;
    return this;
  }

   /**
   * User / Group may update meta data of nodes: rename files and folders, change classification, etc.
   * @return change
  **/
  @ApiModelProperty(example = "false", required = true, value = "User / Group may update meta data of nodes: rename files and folders, change classification, etc.")
  public Boolean getChange() {
    return change;
  }

  public void setChange(Boolean change) {
    this.change = change;
  }

  public NodePermissions delete(Boolean delete) {
    this.delete = delete;
    return this;
  }

   /**
   * User / Group may overwrite and remove files / folders, move files from this room.
   * @return delete
  **/
  @ApiModelProperty(example = "false", required = true, value = "User / Group may overwrite and remove files / folders, move files from this room.")
  public Boolean getDelete() {
    return delete;
  }

  public void setDelete(Boolean delete) {
    this.delete = delete;
  }

  public NodePermissions manageDownloadShare(Boolean manageDownloadShare) {
    this.manageDownloadShare = manageDownloadShare;
    return this;
  }

   /**
   * User / Group may create Download Shares for files and containers view all previously created Download Shares in this room.
   * @return manageDownloadShare
  **/
  @ApiModelProperty(example = "false", required = true, value = "User / Group may create Download Shares for files and containers view all previously created Download Shares in this room.")
  public Boolean getManageDownloadShare() {
    return manageDownloadShare;
  }

  public void setManageDownloadShare(Boolean manageDownloadShare) {
    this.manageDownloadShare = manageDownloadShare;
  }

  public NodePermissions manageUploadShare(Boolean manageUploadShare) {
    this.manageUploadShare = manageUploadShare;
    return this;
  }

   /**
   * User / Group may create Upload Shares for containers, view all previously created Upload Shares in this room.
   * @return manageUploadShare
  **/
  @ApiModelProperty(example = "false", required = true, value = "User / Group may create Upload Shares for containers, view all previously created Upload Shares in this room.")
  public Boolean getManageUploadShare() {
    return manageUploadShare;
  }

  public void setManageUploadShare(Boolean manageUploadShare) {
    this.manageUploadShare = manageUploadShare;
  }

  public NodePermissions readRecycleBin(Boolean readRecycleBin) {
    this.readRecycleBin = readRecycleBin;
    return this;
  }

   /**
   * User / Group may look up files / folders in the Recycle Bin.
   * @return readRecycleBin
  **/
  @ApiModelProperty(example = "false", required = true, value = "User / Group may look up files / folders in the Recycle Bin.")
  public Boolean getReadRecycleBin() {
    return readRecycleBin;
  }

  public void setReadRecycleBin(Boolean readRecycleBin) {
    this.readRecycleBin = readRecycleBin;
  }

  public NodePermissions restoreRecycleBin(Boolean restoreRecycleBin) {
    this.restoreRecycleBin = restoreRecycleBin;
    return this;
  }

   /**
   * User / Group may restore files / folders from Recycle Bin - room permissions required.
   * @return restoreRecycleBin
  **/
  @ApiModelProperty(example = "false", required = true, value = "User / Group may restore files / folders from Recycle Bin - room permissions required.")
  public Boolean getRestoreRecycleBin() {
    return restoreRecycleBin;
  }

  public void setRestoreRecycleBin(Boolean restoreRecycleBin) {
    this.restoreRecycleBin = restoreRecycleBin;
  }

  public NodePermissions deleteRecycleBin(Boolean deleteRecycleBin) {
    this.deleteRecycleBin = deleteRecycleBin;
    return this;
  }

   /**
   * User / Group may permanently remove files / folders from the Recycle Bin.
   * @return deleteRecycleBin
  **/
  @ApiModelProperty(example = "false", required = true, value = "User / Group may permanently remove files / folders from the Recycle Bin.")
  public Boolean getDeleteRecycleBin() {
    return deleteRecycleBin;
  }

  public void setDeleteRecycleBin(Boolean deleteRecycleBin) {
    this.deleteRecycleBin = deleteRecycleBin;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NodePermissions nodePermissions = (NodePermissions) o;
    return Objects.equals(this.manage, nodePermissions.manage) &&
        Objects.equals(this.read, nodePermissions.read) &&
        Objects.equals(this.create, nodePermissions.create) &&
        Objects.equals(this.change, nodePermissions.change) &&
        Objects.equals(this.delete, nodePermissions.delete) &&
        Objects.equals(this.manageDownloadShare, nodePermissions.manageDownloadShare) &&
        Objects.equals(this.manageUploadShare, nodePermissions.manageUploadShare) &&
        Objects.equals(this.readRecycleBin, nodePermissions.readRecycleBin) &&
        Objects.equals(this.restoreRecycleBin, nodePermissions.restoreRecycleBin) &&
        Objects.equals(this.deleteRecycleBin, nodePermissions.deleteRecycleBin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(manage, read, create, change, delete, manageDownloadShare, manageUploadShare, readRecycleBin, restoreRecycleBin, deleteRecycleBin);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NodePermissions {\n");

    sb.append("    manage: ").append(toIndentedString(manage)).append("\n");
    sb.append("    read: ").append(toIndentedString(read)).append("\n");
    sb.append("    create: ").append(toIndentedString(create)).append("\n");
    sb.append("    change: ").append(toIndentedString(change)).append("\n");
    sb.append("    delete: ").append(toIndentedString(delete)).append("\n");
    sb.append("    manageDownloadShare: ").append(toIndentedString(manageDownloadShare)).append("\n");
    sb.append("    manageUploadShare: ").append(toIndentedString(manageUploadShare)).append("\n");
    sb.append("    readRecycleBin: ").append(toIndentedString(readRecycleBin)).append("\n");
    sb.append("    restoreRecycleBin: ").append(toIndentedString(restoreRecycleBin)).append("\n");
    sb.append("    deleteRecycleBin: ").append(toIndentedString(deleteRecycleBin)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}
