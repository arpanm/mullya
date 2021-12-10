import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities as getCatalogues } from 'app/entities/catalogue/catalogue.reducer';
import { getEntity, updateEntity, createEntity, reset } from './catalogue.reducer';
import { ICatalogue } from 'app/shared/model/catalogue.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CatalogueUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const catalogues = useAppSelector(state => state.catalogue.entities);
  const catalogueEntity = useAppSelector(state => state.catalogue.entity);
  const loading = useAppSelector(state => state.catalogue.loading);
  const updating = useAppSelector(state => state.catalogue.updating);
  const updateSuccess = useAppSelector(state => state.catalogue.updateSuccess);
  const handleClose = () => {
    props.history.push('/catalogue');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCatalogues({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...catalogueEntity,
      ...values,
      parent: catalogues.find(it => it.id.toString() === values.parent.toString()),
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
          ...catalogueEntity,
          parent: catalogueEntity?.parent?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mulyaaApp.catalogue.home.createOrEditLabel" data-cy="CatalogueCreateUpdateHeading">
            <Translate contentKey="mulyaaApp.catalogue.home.createOrEditLabel">Create or edit a Catalogue</Translate>
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
                  id="catalogue-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('mulyaaApp.catalogue.name')} id="catalogue-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('mulyaaApp.catalogue.stockImageUrl')}
                id="catalogue-stockImageUrl"
                name="stockImageUrl"
                data-cy="stockImageUrl"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.catalogue.landingUrl')}
                id="catalogue-landingUrl"
                name="landingUrl"
                data-cy="landingUrl"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.catalogue.description')}
                id="catalogue-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.catalogue.isActive')}
                id="catalogue-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('mulyaaApp.catalogue.createdOn')}
                id="catalogue-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.catalogue.createdBy')}
                id="catalogue-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('mulyaaApp.catalogue.updatedOn')}
                id="catalogue-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('mulyaaApp.catalogue.updatedBy')}
                id="catalogue-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                id="catalogue-parent"
                name="parent"
                data-cy="parent"
                label={translate('mulyaaApp.catalogue.parent')}
                type="select"
              >
                <option value="" key="0" />
                {catalogues
                  ? catalogues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/catalogue" replace color="info">
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

export default CatalogueUpdate;
