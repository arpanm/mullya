import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './banner.reducer';
import { IBanner } from 'app/shared/model/banner.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BannerUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bannerEntity = useAppSelector(state => state.banner.entity);
  const loading = useAppSelector(state => state.banner.loading);
  const updating = useAppSelector(state => state.banner.updating);
  const updateSuccess = useAppSelector(state => state.banner.updateSuccess);
  const handleClose = () => {
    props.history.push('/banner');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...bannerEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...bannerEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mullyaApp.banner.home.createOrEditLabel" data-cy="BannerCreateUpdateHeading">
            <Translate contentKey="mullyaApp.banner.home.createOrEditLabel">Create or edit a Banner</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="banner-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('mullyaApp.banner.name')} id="banner-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('mullyaApp.banner.imageUrl')}
                id="banner-imageUrl"
                name="imageUrl"
                data-cy="imageUrl"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.banner.mobileImageUrl')}
                id="banner-mobileImageUrl"
                name="mobileImageUrl"
                data-cy="mobileImageUrl"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.banner.landingUrl')}
                id="banner-landingUrl"
                name="landingUrl"
                data-cy="landingUrl"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.banner.description')}
                id="banner-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label={translate('mullyaApp.banner.html')} id="banner-html" name="html" data-cy="html" type="text" />
              <ValidatedField
                label={translate('mullyaApp.banner.mobileHtml')}
                id="banner-mobileHtml"
                name="mobileHtml"
                data-cy="mobileHtml"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.banner.isActive')}
                id="banner-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('mullyaApp.banner.startDate')}
                id="banner-startDate"
                name="startDate"
                data-cy="startDate"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.banner.endDate')}
                id="banner-endDate"
                name="endDate"
                data-cy="endDate"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.banner.createdOn')}
                id="banner-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('mullyaApp.banner.createdBy')}
                id="banner-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.banner.updatedOn')}
                id="banner-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('mullyaApp.banner.updatedBy')}
                id="banner-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/banner" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BannerUpdate;
