import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IActor } from 'app/shared/model/actor.model';
import { getEntities as getActors } from 'app/entities/actor/actor.reducer';
import { getEntity, updateEntity, createEntity, reset } from './requirement.reducer';
import { IRequirement } from 'app/shared/model/requirement.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { StockCategory } from 'app/shared/model/enumerations/stock-category.model';
import { RequirementStatus } from 'app/shared/model/enumerations/requirement-status.model';

export const RequirementUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const actors = useAppSelector(state => state.actor.entities);
  const requirementEntity = useAppSelector(state => state.requirement.entity);
  const loading = useAppSelector(state => state.requirement.loading);
  const updating = useAppSelector(state => state.requirement.updating);
  const updateSuccess = useAppSelector(state => state.requirement.updateSuccess);
  const stockCategoryValues = Object.keys(StockCategory);
  const requirementStatusValues = Object.keys(RequirementStatus);
  const handleClose = () => {
    props.history.push('/requirement');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getActors({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...requirementEntity,
      ...values,
      actor: actors.find(it => it.id.toString() === values.actor.toString()),
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
          category: 'Rice',
          status: 'New',
          ...requirementEntity,
          actor: requirementEntity?.actor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mullyaApp.requirement.home.createOrEditLabel" data-cy="RequirementCreateUpdateHeading">
            <Translate contentKey="mullyaApp.requirement.home.createOrEditLabel">Create or edit a Requirement</Translate>
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
                  id="requirement-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mullyaApp.requirement.category')}
                id="requirement-category"
                name="category"
                data-cy="category"
                type="select"
              >
                {stockCategoryValues.map(stockCategory => (
                  <option value={stockCategory} key={stockCategory}>
                    {translate('mullyaApp.StockCategory.' + stockCategory)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('mullyaApp.requirement.variant')}
                id="requirement-variant"
                name="variant"
                data-cy="variant"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.requirement.subVariant')}
                id="requirement-subVariant"
                name="subVariant"
                data-cy="subVariant"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.requirement.minPrice')}
                id="requirement-minPrice"
                name="minPrice"
                data-cy="minPrice"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.requirement.maxPrice')}
                id="requirement-maxPrice"
                name="maxPrice"
                data-cy="maxPrice"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.requirement.quantityKg')}
                id="requirement-quantityKg"
                name="quantityKg"
                data-cy="quantityKg"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.requirement.neededBy')}
                id="requirement-neededBy"
                name="neededBy"
                data-cy="neededBy"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.requirement.description')}
                id="requirement-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.requirement.createdOn')}
                id="requirement-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('mullyaApp.requirement.createdBy')}
                id="requirement-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.requirement.updatedOn')}
                id="requirement-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('mullyaApp.requirement.updatedBy')}
                id="requirement-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('mullyaApp.requirement.status')}
                id="requirement-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {requirementStatusValues.map(requirementStatus => (
                  <option value={requirementStatus} key={requirementStatus}>
                    {translate('mullyaApp.RequirementStatus.' + requirementStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="requirement-actor"
                name="actor"
                data-cy="actor"
                label={translate('mullyaApp.requirement.actor')}
                type="select"
              >
                <option value="" key="0" />
                {actors
                  ? actors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/requirement" replace color="info">
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

export default RequirementUpdate;
