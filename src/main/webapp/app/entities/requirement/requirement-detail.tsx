import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './requirement.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RequirementDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const requirementEntity = useAppSelector(state => state.requirement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="requirementDetailsHeading">
          <Translate contentKey="mulyaaApp.requirement.detail.title">Requirement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.id}</dd>
          <dt>
            <span id="minPrice">
              <Translate contentKey="mulyaaApp.requirement.minPrice">Min Price</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.minPrice}</dd>
          <dt>
            <span id="maxPrice">
              <Translate contentKey="mulyaaApp.requirement.maxPrice">Max Price</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.maxPrice}</dd>
          <dt>
            <span id="quantityKg">
              <Translate contentKey="mulyaaApp.requirement.quantityKg">Quantity Kg</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.quantityKg}</dd>
          <dt>
            <span id="neededBy">
              <Translate contentKey="mulyaaApp.requirement.neededBy">Needed By</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.neededBy}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="mulyaaApp.requirement.description">Description</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.description}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mulyaaApp.requirement.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {requirementEntity.createdOn ? (
              <TextFormat value={requirementEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mulyaaApp.requirement.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.createdBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mulyaaApp.requirement.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {requirementEntity.updatedOn ? (
              <TextFormat value={requirementEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mulyaaApp.requirement.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.updatedBy}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="mulyaaApp.requirement.status">Status</Translate>
            </span>
          </dt>
          <dd>{requirementEntity.status}</dd>
          <dt>
            <Translate contentKey="mulyaaApp.requirement.buyerAddress">Buyer Address</Translate>
          </dt>
          <dd>{requirementEntity.buyerAddress ? requirementEntity.buyerAddress.id : ''}</dd>
          <dt>
            <Translate contentKey="mulyaaApp.requirement.buyerUser">Buyer User</Translate>
          </dt>
          <dd>{requirementEntity.buyerUser ? requirementEntity.buyerUser.id : ''}</dd>
          <dt>
            <Translate contentKey="mulyaaApp.requirement.category">Category</Translate>
          </dt>
          <dd>{requirementEntity.category ? requirementEntity.category.id : ''}</dd>
          <dt>
            <Translate contentKey="mulyaaApp.requirement.variant">Variant</Translate>
          </dt>
          <dd>{requirementEntity.variant ? requirementEntity.variant.id : ''}</dd>
          <dt>
            <Translate contentKey="mulyaaApp.requirement.subVariant">Sub Variant</Translate>
          </dt>
          <dd>{requirementEntity.subVariant ? requirementEntity.subVariant.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/requirement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/requirement/${requirementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RequirementDetail;
